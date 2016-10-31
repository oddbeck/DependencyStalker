import { Component } from '@angular/core';

@Component({
  selector: 'my-app',
  templateUrl : '../html/homepage.html',
})
export class DSHomePage {

  private tabularElements : string[] = ["odd","arne","beck"]

  public getTabulatorIndex(n) {
    console.log("User clicked " + this.tabularElements[n] + ", and this is the slot()");
  }

}