import { Directive, Host, Self, Optional, Input, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';
import { GridComponent, SelectableSettings, SelectionEvent } from '@progress/kendo-angular-grid';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

/** This directive makes it possible to two-way bind the single selection of a kendo-grid component to a list. 
 * Use by adding [(kendoGridSingleSelection)]="my_selection" to a <kendo-grid> tag. The grid will be automatically initialized for single selection.
 */
@Directive({ selector: '[kendoGridSingleSelection]' })
export class KendoGridSingleSelectionDirective implements OnInit, OnDestroy {
    private ngUnsubscribe = new Subject();
    @Input("kendoGridSingleSelection") public selection: any;
    @Output("kendoGridSingleSelectionChange") public selectionChange: EventEmitter<any> = new EventEmitter();
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
            mode: 'single',
            checkboxOnly: prevSel && (<SelectableSettings>prevSel).checkboxOnly
        } as SelectableSettings;
        this.grid.rowSelected = (row: { dataItem, index }) => this.selection && this.toField(this.selection) === this.toField(row.dataItem);
        this.grid.selectionChange.pipe(takeUntil(this.ngUnsubscribe)).subscribe(selectionEvent => {
            let newSel = this.processSelectionEventForSingleSelection(selectionEvent);
            this.selectionChange.next(newSel);
        });
        (<any>this.grid).changeNotification.changes.subscribe(() => {
            if (this.idField && this.selection) {
                this.selection = (<any[]>this.grid.data).find(d => this.toField(this.selection) === this.toField(d));
                this.selectionChange.next(this.selection);
            }
        });
    }

    public processSelectionEventForSingleSelection<T>(event: SelectionEvent): T {
        const newValue = (event.selectedRows && event.selectedRows.length ? event.selectedRows[0].dataItem : null) as T;
        return newValue;
    }

    private toField(data: any): any {
        return this.idField ? data[this.idField] : data;
    }
}