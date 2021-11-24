import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-enum-label',
  templateUrl: './enum-label.component.html',
  styleUrls: ['./enum-label.component.scss']
})
export class EnumLabelComponent implements OnInit {

  @Input() public enumType: any;

  @Input() value: any;

  constructor() { }

  ngOnInit() {
  }

  public get enumLabel(): string {
    return (this.enumType._labels).find(l=>l.value === this.value)?.label;
  }

}

