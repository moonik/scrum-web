import {Component, Input, Output, OnInit, EventEmitter} from '@angular/core';

@Component({
  selector: 'app-warning-modal',
  templateUrl: './warning-modal.component.html',
  styleUrls: ['./warning-modal.component.css']
})
export class WarningModalComponent implements OnInit {

  @Input()
  public obj;
  @Output()
  public clickYes = new EventEmitter<Object>();

  constructor() { }

  ngOnInit() {
    console.log(this.obj);
  }

  onYesClicked() {
    console.log(this.obj);
    this.clickYes.emit(this.obj);
  }
}
