import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {DSCategory} from "./../model/dscategory";
import {CategoryService} from "./../service/category.service";


@Component({
    selector: 'dscategory',
    templateUrl: '../html/dscategory.html',
    providers: [CategoryService]
})

export class DSCategoryComp implements OnInit {
    @Input()
    dsCategory: DSCategory;

    @Output()
    onCategoryDeleted: EventEmitter<any> = new EventEmitter();

    @Output()
    isSelected: EventEmitter<any> = new EventEmitter();

    constructor(private categoryService: CategoryService) {
    }
    errorMessage : string = "";
    ngOnInit() {

    }

    public wasSelected() {
        this.isSelected.emit();    
    }
    public saveNewCategory() {
        this.categoryService.saveNewCategory(this.dsCategory).subscribe(data => {
            console.log(JSON.stringify(data));
        });
        this.dsCategory.description = '';
        this.dsCategory.shortname = '';
    }

    public deleteMe() {
        this.categoryService.deleteCategory(this.dsCategory).subscribe(d => {

            if (d == "OK") {
                this.onCategoryDeleted.emit();
                this.dsCategory.description = '';
                this.dsCategory.id = 0;
                this.dsCategory.shortname = '';
            } else {
                console.log("triggering delete error emitter: " + d);
                this.errorMessage = d;
            }

        })   
    }

}
