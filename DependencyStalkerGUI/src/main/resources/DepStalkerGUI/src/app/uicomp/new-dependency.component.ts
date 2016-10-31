import { Component } from '@angular/core';
import { DSUnit } from './../model/dsunit';
import { Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { DepStalkService } from './../service/depstalk.service';


@Component({
    selector: 'new-dependency',
    templateUrl: '../html/new-dependency.html'
})

export class NewDependencyComp {
    @Input()
    private dependencyParent : DSUnit;
    private dependencyChild : DSUnit = { id: 0, shortname : '', description:'', dependencyId:0, directDependency : false };
    private searchResults: DSUnit[] = [];

    @Output()
    onDependencySaved = new EventEmitter();

    private resetDepChild() {
        this.dependencyChild = new DSUnit();
    }

    constructor( private depstalkSvc: DepStalkService) {}

    public saveDependency() : void {
        console.log("child: " + JSON.stringify(this.dependencyChild));
        this.depstalkSvc.postNewDependency(this.dependencyParent,this.dependencyChild).subscribe(d => {
            this.resetDepChild();
            this.onDependencySaved.emit();
        })
    }

    public findServerMatch() {
        let servername = this.dependencyChild.shortname;
        if (servername.length < 1) {
            this.searchResults = [];
            return;
        }
        this.depstalkSvc.getDSUnitsMatching(servername + '*').subscribe(d => {
            this.searchResults = d;
        });
    }

    public selectServer(serverName) {
        this.dependencyChild = serverName;
        this.searchResults = [];
    }
}
