import { Component, OnInit, ViewChild } from '@angular/core';
import { NzModalRef, NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { SFSchema, SFUISchema, SFComponent } from '@delon/form';
import { DataProcessorService } from 'app/service/core/data-processor.service';
import { IDataProcessor, State } from 'app/model/core/data-processor.model';

import * as moment from 'moment';

@Component({
  selector: 'app-data-processor-create',
  templateUrl: './create.component.html',
})
export class DataProcessorCreateComponent implements OnInit {
  loading = false;
  dataProcessor: IDataProcessor = {};

  @ViewChild('dataProcessorSf') dataProcessorSf: SFComponent;

  dataProcessorSchema: SFSchema = {
    properties: {
      nameSpace: { type: 'string', title: '命名空间' },
      name: { type: 'string', title: '名称', maxLength: 15 },
      identifier: { type: 'string', title: '标识符' },
    },
    required: ['nameSpace', 'name', 'identifier'],
  };

  dataProcessorUI: SFUISchema = {
    '*': { spanLabelFixed: 90 },
  };

  constructor(
    private modal: NzModalRef,
    private msgSrv: NzMessageService,
    public http: _HttpClient,
    private dataProcessorService: DataProcessorService,
  ) {}

  ngOnInit(): void {}

  save(formData: IDataProcessor) {
    this.loading = true;
    formData.state = State.DRAFT;
    formData.restApi = false;
    formData.createTs = moment(Date.now());
    formData.updateTs = moment(Date.now());
    this.dataProcessorService.create(formData).subscribe(resp => {
      this.loading = false;
      this.msgSrv.success('保存成功');
      this.modal.close(resp.body);
    });
  }

  close() {
    this.modal.destroy();
  }
}
