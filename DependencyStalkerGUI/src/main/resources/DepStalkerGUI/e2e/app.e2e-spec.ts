import { DepStalkerGUIPage } from './app.po';

describe('dep-stalker-gui App', function() {
  let page: DepStalkerGUIPage;

  beforeEach(() => {
    page = new DepStalkerGUIPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
