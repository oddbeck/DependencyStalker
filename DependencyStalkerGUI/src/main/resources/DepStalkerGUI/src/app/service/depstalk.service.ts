import { Injectable } from '@angular/core';
import { DSUnit } from '../model/dsunit';
import { Headers, Http, Response } from '@angular/http';

// Observable operators
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import 'rxjs/Rx';
import { DSCategory } from './../model/dscategory';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class DepStalkService {

    public SERVER_URL = 'http://localhost:8080/'
    private POST_NEW = this.SERVER_URL + 'saveUnit';
    private GET_ITEMS = this.SERVER_URL + 'getDSUnit/'
    private SEARCH_FOR_MATCHING_ITEMS = this.SERVER_URL + 'searchForDSUnitByName/';
    private GET_ITEM_BY_ID = this.SERVER_URL + 'getDSUnitById/';
    private GET_DEPENDENCIES_BY_ID = this.SERVER_URL + '/resolveDepsForId/';
    private GET_REVERSE_DEPENDENCIES_BY_ID = this.SERVER_URL + '/reverseResolveDepsForId/';
    private POST_DEPENDENCY = this.SERVER_URL + 'saveDependency';
    private DELETE_DEPENDENCY = this.SERVER_URL + 'deleteDependencyById/';
    private POST_NEW_CATEGORY = this.SERVER_URL + 'saveNewCategory';
    private GET_CATEGORY_LIST_BY_NAME = this.SERVER_URL + 'getCategoryListByName/';
    private PUT_NEW_CATEGORY_ON_UNIT = this.SERVER_URL + 'putNewCategoryOnItem/';
    private UPDATE_DSUNIT_INFO = this.SERVER_URL + "updateUnit";
    private GET_CATEGORY_FOR_UNIT = this.SERVER_URL + 'getCategoryForUnit/';

    constructor(private http: Http) { }

    postNewDSUnit(dsunit): Observable<DSUnit> {
        return this.http.post(this.POST_NEW, dsunit).map(((r: Response) => {
            return r.json() as DSUnit;
        }));
    }

    postNewDependency(parent, child): Observable<string> {
        let dependency = {
            parent: parent,
            child: child
        }
        return this.http.post(this.POST_DEPENDENCY, dependency).map(((r: Response) => {
            return r.json() as string;
        }));
    }

    getDSUnitsMatching(unitname): Observable<DSUnit[]> {
        return this.http.get(this.SEARCH_FOR_MATCHING_ITEMS + unitname)
            .map((r: Response) => r.json() as DSUnit[]);
    }

    getDsUnitById(id): Observable<DSUnit> {
        return this.http.get(this.GET_ITEM_BY_ID + id)
            .map((r: Response) => r.json() as DSUnit);
    }

    getDependenciesById(id): Observable<DSUnit[]> {
        return this.http.get(this.GET_DEPENDENCIES_BY_ID + id).map(((r: Response) => {
            return r.json() as DSUnit[];
        }));
    }

    getReverseDependenciesById(id): Observable<DSUnit[]> {
        return this.http.get(this.GET_REVERSE_DEPENDENCIES_BY_ID + id).map(((r: Response) => {
            return r.json() as DSUnit[];
        }));
    }

    deleteDependencyByDepId(id): Observable<Response> {
        return this.http.delete(this.DELETE_DEPENDENCY + id);
    }

    saveNewCategory(cat: DSCategory): Observable<String> {

        return this.http.post(this.POST_NEW_CATEGORY, cat).map(((r: Response) => {
            return r.json() as string;
        }));
    }

    saveCategoryOnDsUnit(unit: DSUnit, category: DSCategory) {
        let postURI = this.PUT_NEW_CATEGORY_ON_UNIT + unit.id + '/' + category.id;
        return this.http.put(postURI, null).map(((r: Response) => {
            return r.json() as string;
        }));
    }

    getCategoriesFromName(name: string): Observable<DSCategory[]> {
        if (name.endsWith("*") != true) {
            name += "*";
        }
        return this.http.get(this.GET_CATEGORY_LIST_BY_NAME + name).map(((r: Response) => {
            return r.json() as DSCategory[];
        }));
    }

    getCategoryForUnit(unitId: number): Observable<DSCategory> {
        return this.http.get(this.GET_CATEGORY_FOR_UNIT + unitId)
            .map((r: Response) => {
                if (r.status == 200) {
                    return r.json() as DSCategory
                } else {
                    return null;
                }
            }
            ).catch(this.handleError);
    }

    private handleError(error: any) : Observable<any> {
        let errMsg = (error.message) ? error.message :
            error.status ? `${error.status} - ${error.statusText}` : 'Server error';
        return Observable.of(errMsg);
    }



    updateUnit(unit: DSUnit) {
        let postURI = this.UPDATE_DSUNIT_INFO;
        return this.http.put(postURI, unit).map(((r: Response) => {
            return r.json() as string;
        }));
    }
}