import { Component, OnInit, HostListener, AfterViewInit, AfterViewChecked, OnDestroy } from '@angular/core';
import { BasePageInterface } from './base-page.interface';
import { Router, ActivatedRoute } from '@angular/router';
import { ReuseTabService, STChangeSort } from '@delon/abc';
import { EventBusService } from 'app/service/event-bus.service';
import { TranslateService } from '@ngx-translate/core';
import { TitleService } from '@delon/theme';

@Component({
  selector: 'app-base-page',
  template: '',
  styles: []
})
export class BasePageComponent implements OnInit, AfterViewInit, BasePageInterface, OnDestroy {

  loading = true;
  headerTitle: string = null;

  currentUrl: string = null;
  previousUrl: string = null;

  multiSort = {
    separator: '||',
    nameSeparator: ',',
  };


  constructor(
    protected router: Router,
    protected activatedRoute: ActivatedRoute,
    protected reuseTabService: ReuseTabService,
    protected eventBusService: EventBusService,
    protected translateService: TranslateService,
    protected titleService: TitleService,
  ) {

  }

  ngOnInit() {
    if (!this.reuseTabService['tabCount']) this.reuseTabService['tabCount'] = 1;
    else this.reuseTabService['tabCount']++;

    this.eventBusService.queue.subscribe(event => this.eventHandler(event));

    this.currentUrl = this.router.url;
    this.previousUrl = this.activatedRoute.snapshot.queryParams['fromUrl'];

    this.initVariable(this.activatedRoute.snapshot.params, this.activatedRoute.snapshot.queryParams);
    this.loadPage();
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.windowResizeListener();
    }, 300);
  }

  ngOnDestroy(): void {
    this.reuseTabService['tabCount']--;

  }

  translate(key) {
    return this.translateService.instant(key);
  }

  goToPrevious(): void {
    if (!this.previousUrl) return;
    this.router.navigateByUrl(this.previousUrl);
  }

  close(): void {
    if (this.reuseTabService['tabCount'] <= 1) return;
    this.reuseTabService.close(this.router.url.split('?')[0], false);
  }

  navigate(url: string, queryParams: any): void {
    if (!queryParams) queryParams = {};

    queryParams['fromUrl'] = this.currentUrl;

    this.router.navigate([url], {queryParams});
  }

  setTitle(titleI18n: string, tabSuffix: string): void {
    this.translateService.get(titleI18n).subscribe(text => {
      const title = !tabSuffix ? text : text + '-' + tabSuffix;
      this.reuseTabService.title = title;
      this.titleService.setTitle(title);
    });
  }

  emitEvent(event: string): void {
    this.eventBusService.queue.next(event);
  }

  // to be overwrite
  initVariable(params, queryParams): void {
    throw new Error('Method not implemented.');
  }

  loadPage(): void {
    throw new Error('Method not implemented.');
  }

  eventHandler(event: string): void {
    throw new Error('Method not implemented.');
  }

  convertSortData(sort: STChangeSort): string[] {
    return sort.map['sort'].replace(/ascend/g, 'asc').replace(/descend/g, 'desc').split('||');
  }

  onWindowResize(event) {}

  @HostListener('window:resize', ['$event'])
  windowResizeListener(event?) {
    this.onWindowResize(event);
  }

}
