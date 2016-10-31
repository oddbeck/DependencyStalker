import { Injectable } from '@angular/core';
import { DSUnit } from '../model/dsunit';
import { Headers, Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

// Observable operators
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { DSCategory } from './../model/dscategory';

@Injectable()
export class CategoryService {

    public SERVER_URL = 'http://localhost:8080/'
    private POST_NEW_CATEGORY = this.SERVER_URL + 'saveCategory';
    private GET_CATEGORY_LIST_BY_NAME = this.SERVER_URL + 'getCategoryListByName/';
    private PUT_NEW_CATEGORY_ON_UNIT = this.SERVER_URL + 'putNewCategoryOnItem/';
    private GET_CATEGORY_FOR_UNIT = this.SERVER_URL + 'getCategoryForUnit/';
    private GET_ALL_CATEGORIES = this.SERVER_URL + 'getAllCategories';
    private DELETE_CATEGORY = this.SERVER_URL + 'deleteCategory/';

    constructor(private http: Http) { }

    saveNewCategory(cat : DSCategory) : Observable<Response> {

        return this.http.post(this.POST_NEW_CATEGORY,cat).map(((r: Response) => {
            return r;
        }));
    }

    deleteCategory(cat : DSCategory) : Observable<string> {

        return this.http.delete(this.DELETE_CATEGORY + cat.id,null).map(((r: Response) => {
            return r.text() as string;
        }));
    }


    saveCategoryOnDsUnit(unit: DSUnit, category: DSCategory) {
        let postURI = this.PUT_NEW_CATEGORY_ON_UNIT + unit.id + '/' + category.id;
        return this.http.put(postURI,null).map(((r: Response) => {
            return r.json() as string;
        }));
    }

    getCategoriesFromName(name : string): Observable<DSCategory[]> {
        if (name.endsWith("*") != true) {
            name += "*";
        }
        return this.http.get(this.GET_CATEGORY_LIST_BY_NAME + name).map( ( (r:Response) =>  {
            return r.json() as DSCategory[];
        }));
    }

    getCategoryForUnit(unitId: number ): Observable<DSCategory> {
        return this.http.get(this.GET_CATEGORY_FOR_UNIT + unitId)
            .map((r: Response) => r.json() as DSCategory);
    }
    getAllCategories() : Observable<DSCategory[]> {
        return this.http.get(this.GET_ALL_CATEGORIES)
            .map((r: Response) => r.json() as DSCategory[]);
    }

}