import { Component, OnInit, ViewChild } from '@angular/core';
import { _HttpClient, ModalHelper } from '@delon/theme';
import { STColumn, STComponent } from '@delon/abc';
import { SFSchema } from '@delon/form';

@Component({
  selector: 'app-expand-form-expand',
  templateUrl: './expand.component.html',
})
export class ExpandFormExpandComponent implements OnInit {
  params: any = { name: '项目名称' };
  users: any[] = Array(50)
    .fill({})
    .map((item: any, idx: number) => {
      return {
        id: idx + 10009898,
        name: `项目名称 ${idx + 1}`,
        callNo: Math.floor(Math.random() * 1000),
        status: Math.floor(Math.random() * 10) % 4,
        updatedAt: new Date(`2017-07-${Math.floor(idx/ 2) + 1}`),
        createdAt: new Date(`2017-07-${Math.floor(idx / 2) + 1}`),
        progress: Math.ceil(Math.random() * 100),
        description: `${idx +
        1}. 这是一个项目，项目发起时间是2017-07-${Math.floor(idx/ 2) + 1}，项目负责人是WWWW.`,
      };
    });

  @ViewChild('st') st: STComponent;id
  columns: STColumn[] = [
    { title: '项目编号', index: 'id' },
    { title: '项目名称', index: 'name' },
    { title: '调用次数', type: 'number', index: 'callNo' },
    { title: '创建时间', type: 'date', index: 'updatedAt' },
  ];

  constructor(private http: _HttpClient, private modal: ModalHelper) { }

  ngOnInit() { }

  add() {
    // this.modal
    //   .createStatic(FormEditComponent, { i: { id: 0 } })
    //   .subscribe(() => this.st.reload());
  }

}
