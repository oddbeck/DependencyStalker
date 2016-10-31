/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { TabularComponent } from './tabular.component';

describe('TabularComponetComponent', () => {
  let component: TabularComponent;
  let fixture: ComponentFixture<TabularComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabularComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabularComponent);
    component = fixture.componentInstance;
    component.columns = ['Aktiv', 'Inaktiv'];
    fixture.detectChanges();
  });

  it('selectedTabular should always be 0 to begin with', () => {
    expect(component.currentSelected).toEqual(0);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have active and inactive element', () => {
    let debugElement: HTMLElement =  fixture.debugElement.nativeElement;
    let tab_active = debugElement.querySelector('span.tab_active');
    let tab_inactive = debugElement.querySelector('span.clickable.tab_inactive');

    expect(tab_inactive.textContent).toBe('Inaktiv');
    expect(tab_active.textContent).toBe('Aktiv');
    expect(component).toBeTruthy();
  });

});
