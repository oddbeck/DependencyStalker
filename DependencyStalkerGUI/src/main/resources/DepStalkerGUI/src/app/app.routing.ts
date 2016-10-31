import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddNewDsunitComponent } from './uicomp/new-dsunit.component';
import { AppComponent } from './app.component';
import { DSHomePage } from './uicomp/home-page.component';
import { SearchComponent } from './uicomp/search.component';
import { DSUnitDetails } from './uicomp/dsunit-details.component';
import { AddNewCategoryComp } from './uicomp/new-category.component';

const appRoutes: Routes = [
    { path: 'addNew', component: AddNewDsunitComponent },
    { path: 'addNewCategory', component: AddNewCategoryComp },
    { path: 'search', component: SearchComponent },
    { path: 'dsunitDetails/:id', component: DSUnitDetails },
    { path: '', component : DSHomePage}
];

export const appRoutingProviders: any[] = [

];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);