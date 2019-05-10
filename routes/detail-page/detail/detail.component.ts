import {ChangeDetectorRef,ChangeDetectionStrategy, Component, OnInit, ViewChild} from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';
import {NzMessageService, NzTabChangeEvent} from "ng-zorro-antd";

@Component({
  selector: 'app-detail-page-detail',
  templateUrl: './detail.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailPageDetailComponent implements OnInit {
  url = `/user`;
  list: any[]= [];
  data = {
    advancedOperation1: [],
    advancedOperation2: [],
    advancedOperation3: [],
  };
  @ViewChild('st') st: STComponent;
  columns: STColumn[] = [
    { title: '项目编号', index: 'id' },
    { title: '项目名称', index: 'projectName' },
    { title: '操作类型', index: 'type' },
    { title: '执行结果', index: 'status', render: 'status' },
    { title: '操作时间', type: 'date', index: 'staTime' },
    { title: '备注', index: 'memo' },

  ];

  constructor( public msg:NzMessageService,private http: _HttpClient, private modal: ModalHelper,private cdr:ChangeDetectorRef) { }

  ngOnInit() {
    this.http.get('/detail-page/detail').subscribe((res: any) => {
      this.data = res;
      this.change({ index: 0, tab: null });
      this.cdr.detectChanges();
    });
  }

  add() {
    // this.modal
    //   .createStatic(FormEditComponent, { i: { id: 0 } })
    //   .subscribe(() => this.st.reload());
  }
 change(args:NzTabChangeEvent){
  this.list=this.data[`advancedOperation${args.index+1}`];
 }
}
