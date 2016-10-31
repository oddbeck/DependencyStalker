import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { DSMainMenu } from './uicomp/mainmenu.component';
import { AddNewDsunitComponent } from './uicomp/new-dsunit.component';
import { AddNewCategoryComp } from './uicomp/new-category.component';
import { routing, appRoutingProviders } from './app.routing';
import { SearchComponent } from './uicomp/search.component';
import { DSUnitDetails } from './uicomp/dsunit-details.component';
import { DSHomePage } from './uicomp/home-page.component';
import { DSCategory } from './model/dscategory';
import { DepStalkService } from './service/depstalk.service';
import { DSUnitComp } from './uicomp/dsunit.component';
import { DSCategoryComp } from './uicomp/dscategory.component';
import { NewDependencyComp } from './uicomp/new-dependency.component';
import { TabularComponent } from './tabular-component/tabular.component';

@NgModule({
  declarations: [
    AppComponent, DSMainMenu, AddNewDsunitComponent, AddNewCategoryComp,
    SearchComponent, DSUnitDetails, DSHomePage, DSUnitComp, DSCategoryComp, NewDependencyComp, TabularComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
