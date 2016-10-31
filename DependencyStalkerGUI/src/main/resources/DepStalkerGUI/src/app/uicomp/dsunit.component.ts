import { Component, OnInit } from '@angular/core';
import { Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { DepStalkService } from './../service/depstalk.service';
import { DSCategory } from './../model/dscategory';
import { DSUnit } from '../model/dsunit';


@Component({
    selector: 'dsunit',
    templateUrl: '../html/dsunit.html'
})

export class DSUnitComp implements OnInit {

    @Input()
    dsUnit: DSUnit = null;
    dsNonModifiedUnit: DSUnit = null;
    @Input()
    private showDeleteButton: boolean = false;
    @Input()
    private hideCategoryInfo: boolean = true;
    @Output()
    private categorySearchCompleted = new EventEmitter();
    @Input()
    private selectedCategory: DSCategory = new DSCategory();
    @Input() canBeFollowed : boolean = true;

    private searchText: string;
    private categorySearches: DSCategory[] = [];
    private hasSaved: boolean = true;
    private inEditMode: boolean = false;

    @Output()
    onUnitDeleted: EventEmitter<any> = new EventEmitter();

    constructor(private depstalkSvc: DepStalkService) { }

    public setData() {
        if (this.dsUnit != undefined && this.dsUnit.id != 0) {
            this.depstalkSvc.getCategoryForUnit(this.dsUnit.id).subscribe(d => {
                this.selectedCategory = d;
                return;
            });
        } else {
            console.log("Not setting data" + " " + Math.random());
        }
    }

    ngOnInit() {
        this.setData();
    }

    public isInEditMode(): boolean {
        if (this.dsUnit.description.length == 0) {
            return true;
        }
        return this.inEditMode;
    }
    public deleteUnitDependency() {
        this.depstalkSvc.deleteDependencyByDepId(this.dsUnit.dependencyId).subscribe((res) => {
            this.onUnitDeleted.emit();
        });
    }

    public setSelectedCategory(cat: DSCategory) {
        this.selectedCategory = cat;
        this.hasSaved = false;
    }
    public showCategorySearchResults(): void {
        this.depstalkSvc.getCategoriesFromName(this.searchText).subscribe((d) => {
            this.categorySearches = d;
        }
        );
    }
    
    public showOrHideEditField(inputElement) {
        this.inEditMode = !this.inEditMode || this.dsUnit.description.length == 0;
        this.dsNonModifiedUnit = this.copyDsUnit(this.dsUnit);
        if (this.inEditMode) {
            setTimeout(function () {
                inputElement.focus();
            }, 200);
        }
    }

    public saveCategoryInfo() {
        this.hasSaved = true;
        this.depstalkSvc.saveCategoryOnDsUnit(this.dsUnit, this.selectedCategory).subscribe(d => { });
        this.categorySearches = [];
        this.searchText = '';
    }

    public saveEdits(event: any) {
        this.dsNonModifiedUnit = this.dsUnit;
        this.inEditMode = false;
        this.depstalkSvc.updateUnit(this.dsUnit).subscribe(d => d);
    }

    public onEditBlur(event: any) {
        this.dsUnit = this.dsNonModifiedUnit;
        this.inEditMode = false;
    }

    private copyDsUnit(dsunit: DSUnit) : DSUnit {
        let newUnit = JSON.parse(JSON.stringify(dsunit));
        return newUnit;
    }
}
