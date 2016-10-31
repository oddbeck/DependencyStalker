import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-tabular-component',
  templateUrl: './tabular.component.html',
  styleUrls: ['./tabular.component.css']
})
export class TabularComponent implements OnInit {
  @Input() columns: string[];
  @Output() itemClicked = new EventEmitter<number>();
  currentSelected: number = 0;

  constructor() { }

  changeSelection(n: number) {
    if (n !== this.currentSelected) {
      this.currentSelected = n;
      this.itemClicked.emit(this.currentSelected);
    }
  }

  getDesiredStyleClasses(i: number) {
    if (i === this.currentSelected) {
      return 'noselect tab_active';
    } else {
      return 'clickable tab_inactive';
    }
  }
  ngOnInit() {
  }

}
