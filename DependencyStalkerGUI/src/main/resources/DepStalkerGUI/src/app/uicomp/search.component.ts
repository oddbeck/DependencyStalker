import { Component, OnInit, Input } from '@angular/core';
import { DSUnit } from './../model/dsunit';
import { DepStalkService } from '../service/depstalk.service';

@Component({
  selector: 'searchcomp',
  templateUrl : '../html/searchcomp.html',
  providers: [DepStalkService]
})

export class SearchComponent implements OnInit{

  ngOnInit() {
    
  }

  @Input()
  private dsUnits : DSUnit[] = [];
  private searchWord : string = '';
  private foundMatches : boolean = null;

  constructor( private depstalkSvc: DepStalkService ) {
  }
  public enterKeyup() : void {
    console.log("Do search for: " + this.searchWord);
    if (this.searchWord != null && this.searchWord.length >= 1) {
      this.depstalkSvc.getDSUnitsMatching(this.searchWord).subscribe(d => {
        this.dsUnits = d;
        this.foundMatches = d.length > 0;
        console.log("These are my results: " + JSON.stringify(d));
      });
    } else {
        this.foundMatches = null;
    }
  }

  public stringify(elem) : string {
    return JSON.stringify(elem);
  } 
}