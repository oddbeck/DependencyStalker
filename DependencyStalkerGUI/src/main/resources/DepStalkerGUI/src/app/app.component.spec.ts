/* tslint:disable:no-unused-variable */

import { TestBed, async} from '@angular/core/testing';
import { AppComponent } from './app.component';
import { DSMainMenu } from './uicomp/mainmenu.component';
import { routing } from './app.routing';
import { AddNewDsunitComponent } from './uicomp/new-dsunit.component';
import { AddNewCategoryComp } from './uicomp/new-category.component';
import { SearchComponent } from './uicomp/search.component';
import { DSUnitDetails } from './uicomp/dsunit-details.component';
import { DSHomePage } from './uicomp/home-page.component';
import { FormsModule } from '@angular/forms';
import { DSCategoryComp } from './uicomp/dscategory.component';
import { DSUnitComp } from './uicomp/dsunit.component';
import { NewDependencyComp } from './uicomp/new-dependency.component';
import {APP_BASE_HREF} from '@angular/common';

import { RouterTestingModule} from '@angular/router/testing';
import { by } from 'protractor';


describe('App: DepStalkerGUI', () => {
  // beforeEach(() => {
  //   TestBed.configureTestingModule({
  //     declarations: [
  //       AppComponent, DSMainMenu, AddNewDsunitComponent, AddNewCategoryComp, SearchComponent,
  //       DSUnitDetails, DSHomePage, DSCategoryComp, DSUnitComp, NewDependencyComp
  //     ],
  //     imports: [
  //       FormsModule, RouterTestingModule.withRoutes([])
  //     ],
  //     providers: []
  //   });
  // });
  //   it('should create the app', async(() => {
  //   let fixture = TestBed.createComponent(AppComponent);
  //   let app = fixture.debugElement.componentInstance;
  //   expect(app).toBeTruthy();
  // }));


  // it(`should have as title 'DepStalkerGUI!'`, async(() => {
  //   let fixture = TestBed.createComponent(AppComponent);
  //   let app = fixture.debugElement.componentInstance;
  //   expect(app.title).toEqual('DepStalkerGUI');
  // }));

  // it('should render title in a h1 tag', async(() => {
  //   let fixture = TestBed.createComponent(AppComponent);
  //   fixture.detectChanges();
  //   let compiled = fixture.debugElement.nativeElement;
  //   let element = fixture.nativeElement;
  //   console.log(JSON.stringify(element)  + " : "+ element );
  //   //expect(compiled.querySelector('h1').textContent).toContain('app works!');
  // }));

});
