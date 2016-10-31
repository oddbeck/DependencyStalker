import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CategoryService } from '../service/category.service';
import { DSCategory } from '../model/dscategory';

@Component({
  templateUrl: '../html/new-category.html',
  selector: 'add-new-dsunit',
  providers: [CategoryService]
})

export class AddNewCategoryComp implements OnInit {
  public dsCategory = {
    shortname: '',
    description: '',
    id: 0
  }

  @Output()
  onSaveComplete = new EventEmitter();
  
  buttonInnerText : string = "Save";

  errorMessage: string = "";

  allCategories: DSCategory[] = [];

  constructor(private catService: CategoryService) { }

  ngOnInit() {
    this.loadAllCategories();
  }

  loadAllCategories() {
    this.catService.getAllCategories().subscribe(d => {
      this.allCategories = d;
        this.resetForm();
    });

  }

  public saveNewCategory() {
    this.errorMessage = "";

    if (this.dsCategory.description.length == 0 || this.dsCategory.shortname.length == 0) {
      this.errorMessage = "Please make sure all form elements contains data.";
    } else {
      this.catService.saveNewCategory(this.dsCategory).subscribe(data => {
        this.loadAllCategories();
      });
    }
  }

  public resetForm() {
      this.buttonInnerText = 'Save';
      this.dsCategory = {
        description : '',
        id: 0,
        shortname : ''
      }
  }

  public setEditableItem(item) {
    this.buttonInnerText = 'Update';
    this.dsCategory = item;
    console.log(JSON.stringify(item));
  }

}