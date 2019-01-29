import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ResultComponent, STColumn } from '@delon/abc';

import 'codemirror/mode/javascript/javascript';
import { CodemirrorService } from '@nomadreservations/ngx-codemirror';
import { Editor } from 'codemirror';
import { LayoutService } from 'app/service/layout.service';


@Component({
  selector: 'app-data-sub-processor-result',
  templateUrl: './data-sub-processor-result.component.html',
  styles: []
})
export class DataSubProcessorResultComponent implements OnInit, OnChanges {

  @Input() result;

  resultStColumn: STColumn[] = [{ title: '' }];
  stData: any = [];

  resultJson = '';

  // #region CodeMirrorEditor
  cmEditor: Editor = null;

  cmOptions: any = {
    readOnly: true,
    lineNumbers: true,
    mode: { name: 'text/javascript' },
    theme: 'dracula'
  };
  // #endregion

  constructor(
    private layoutService: LayoutService,
    private codemirrorService: CodemirrorService,
  ) { }

  ngOnInit() {
    this.codemirrorService.instance$.subscribe((editor) => {
      this.cmEditor = editor;
      editor.setSize('100%', `calc(100vh - ${this.layoutService.contentHeightOffset}px - 37px)`);
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['result'] && !changes['result']['firstChange']) {
      this.resultJson = JSON.stringify(this.result, null, 4);
      // this.stData = Object.values(this.data['results'])[0]['content'];

      // this. resultStColumn = Object.keys(this.stData[0]).map(item => {
      //   return {title: item, index: item};
      // });
    }
  }

}
