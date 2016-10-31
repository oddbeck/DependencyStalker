import {DSCategory} from '../model/dscategory';
import { Component, Input } from '@angular/core';
import { DSUnit } from './../model/dsunit';
import { OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { DepStalkService } from '../service/depstalk.service';

@Component({
  templateUrl: '../html/dsunit-details.html',
  selector: 'dsunit-details',
  providers: [DepStalkService]
})

export class DSUnitDetails {
  @Input()
  private dsUnit: DSUnit = null;
  private dependencies : DSUnit[] = null;
  private regularDependencies : DSUnit[] = null;
  private reverseDependencies : DSUnit[] = null;

  private newDep = true;
  private hideCategoryInfo : boolean = true;
  private selectedCategory : DSCategory = {id: 0, shortname:'', description: ' '}
  private hasSaved : boolean = true;
  private categorySearches : DSCategory[] = [];
  private searchText : string = '';
  private showReversedDependencies : boolean = false;
  private reversedLookupClass = 'clickable dsunit_dep_inactive';
  private nonReversedLookupClass = 'dsunit_dep_active';
  

  constructor(private route: ActivatedRoute, private depstalkSvc : DepStalkService) {
    this.dependencies = null;
  }

  ngOnInit(): void {
    console.log("ngOnInit()");
    this.dependencies = null;
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];
      this.depstalkSvc.getDsUnitById(id).subscribe(d => {
        this.dsUnit = d;
        this.dependencies = null;
        this.dependencies = this.regularDependencies;
        this.resolveDependencies();
        this.resolveReverseDependencies();
        // get the category
        this.depstalkSvc.getCategoryForUnit(this.dsUnit.id).subscribe(catData => {
          this.selectedCategory = catData;
        });
      });
    });

  }

  showDesiredDependencies(n:number) {
    if (n == 0) {
      this.dependencies = this.regularDependencies;
      this.showReversedDependencies = false;

    } else {
      this.dependencies = this.reverseDependencies;
      this.showReversedDependencies = true;
    }
  }

  showAddNewdependency() {
    this.newDep = !this.newDep;
  }


  public catchDeletedItemWithResolveDependencies(argh) {
    this.resolveDependencies();
  }

  resolveDependencies() : void {
    console.log("Resolve Dependencies")
    if (this.dsUnit != null && this.dsUnit != undefined && this.dsUnit.id != 0) {
      this.depstalkSvc.getDependenciesById(this.dsUnit.id).subscribe(
        d => {
          this.regularDependencies = d;
          if (!this.showReversedDependencies) {
            this.dependencies = d;
          }
        });
      
    }
  }

  resolveReverseDependencies() : void {
    console.log("Resolve ReverseDendencies")
    if (this.dsUnit != null && this.dsUnit != undefined && this.dsUnit.id != 0) {
      this.depstalkSvc.getReverseDependenciesById(this.dsUnit.id).subscribe(d => {
        this.reverseDependencies = d;
        if (this.showReversedDependencies) {
          this.dependencies = d;
        }

      });
    }
  }
}