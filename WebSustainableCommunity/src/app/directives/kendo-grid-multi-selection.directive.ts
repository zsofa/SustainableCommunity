import { Directive, Host, Self, Optional, Input, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { GridComponent, SelectableSettings, SelectionEvent } from '@progress/kendo-angular-grid';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

/** This directive makes it possible to two-way bind the multiple selection of a kendo-grid component to a list. 
 * Use by adding [(kendoGridMultiSelection)]="my_selection_list" to a <kendo-grid> tag. The grid will be automatically initialized for multiple selection.
 */
@Directive({ selector: '[kendoGridMultiSelection]' })
export class KendoGridMultiSelectionDirective implements OnInit, OnDestroy {
    private ngUnsubscribe = new Subject();
    @Input("kendoGridMultiSelection") public selection: any[];
    @Output("kendoGridMultiSelectionChange") public selectionChange: EventEmitter<any[]> = new EventEmitter();
    @Input() public idField: string;

    constructor(
        @Host() @Self() @Optional() public grid: GridComponent,
    ) { }

    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

    ngOnInit(): void {
        const prevSel = this.grid.selectable;
        this.grid.selectable = {
            enabled: true,
            mode: 'multiple',
            checkboxOnly: prevSel && (<SelectableSettings>prevSel).checkboxOnly
        } as SelectableSettings;
        this.grid.rowSelected = (row: { dataItem, index }) => {
            return this.selection && this.selection.map(s => this.toField(s)).indexOf(this.toField(row.dataItem)) >= 0;
        };
        this.grid.selectionChange.pipe(takeUntil(this.ngUnsubscribe)).subscribe(selectionEvent => {
            const newSel = this.processSelectionEventForMultiSelection(selectionEvent, this.selection);
            this.selectionChange.next(newSel);
        });
        (<any>this.grid).changeNotification.changes.subscribe(() => {
            if (this.idField && this.selection) {
                this.selection = (<any[]>this.grid.data).filter(d => this.selection.some(ns => this.toField(ns) === this.toField(d)));
                this.selectionChange.next(this.selection);
            }
        });
    }

    private processSelectionEventForMultiSelection<T>(event: SelectionEvent, currentSelection: T[]): T[] {
        return (currentSelection ? currentSelection : [])
            .filter(item => (event.deselectedRows === null) || (event.deselectedRows.map(r => r.dataItem as T).indexOf(item) < 0))
            .concat(event.selectedRows.map(row => row.dataItem));
    }

    private toField(data: any): any {
        return this.idField ? data[this.idField] : data;
    }
}