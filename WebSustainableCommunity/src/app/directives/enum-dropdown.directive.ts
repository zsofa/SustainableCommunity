import { Directive, Host, Self, Optional, Input } from "@angular/core";
import { DropDownListComponent } from "@progress/kendo-angular-dropdowns";
import { OnInit } from "@angular/core";
import { getAllEnumValues } from "../helpers/enum-helpers";

@Directive({
    selector: '[enumDropdown]'
})
export class EnumDropdownDirective implements OnInit {
    @Input('enumDropdown') public enumType: any;
    /** Possible values: value, text, none */
    @Input('orderBy') public orderBy: string;
    /** Ignored Enums */
    @Input('kalcEnumIgnored') public kalcEnumIgnored: number[] = [];

    ngOnInit(): void {

        if (!this.ddl)
            return;
        if (!this.enumType)
            return;

        this.ddl.textField = "label";
        this.ddl.valueField = "value";
        this.ddl.valuePrimitive = true;
        this.updateList();
    }

    updateList() {
        if (!this.ddl)
            return;
        if (!this.enumType)
            return;
        let data = this.enumType._labels;
        if (this.orderBy) {
            if (this.orderBy.toLowerCase() === "value")
                data = data.sort((a, b) => a.value - b.value);
            if (this.orderBy.toLowerCase() === 'text')
                data = data.sort((a, b) => a.text.localeCompare(b.text));
        }
        this.ddl.data = data;

    }


    constructor(@Host() @Self() @Optional() public ddl: DropDownListComponent) {

    }
}
