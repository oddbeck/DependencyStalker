import { Component } from '@angular/core';
import { DepStalkService } from './../service/depstalk.service';
import { DSUnit } from '../model/dsunit';

@Component({
    templateUrl: '../html/new-dsunit.html',
    selector: 'add-new-dsunit',
    providers: [DepStalkService]
})

export class AddNewDsunitComponent {
    dsUnit: DSUnit = this.resetDsUnit();

    allDsUnits = [];

    constructor(private depstalkSvc: DepStalkService) { }

    public saveNewDSUnit() {
        this.depstalkSvc.postNewDSUnit(this.dsUnit).subscribe(data => {
            this.dsUnit = <DSUnit>data;
            this.resetDsUnit();
        });
    }

    resetDsUnit() {
        this.dsUnit = new DSUnit();
        return this.dsUnit;
    }
}