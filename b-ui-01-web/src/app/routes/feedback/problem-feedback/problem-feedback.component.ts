import { Component, ChangeDetectionStrategy, OnInit, ChangeDetectorRef  } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import {NzMessageService} from "ng-zorro-antd";


@Component({
  selector: 'app-feedback-problem-feedback',
  templateUrl: './problem-feedback.component.html',
  styleUrls:['./problem-feedback.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FeedbackProblemFeedbackComponent implements OnInit {
  form: FormGroup;
  submitting = false;

  constructor(private fb: FormBuilder, private msg: NzMessageService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      title: [null, [Validators.required]],
      date: [null, [Validators.required]],
      goal: [null, [Validators.required]],
      standard: [null, [Validators.required]],
      client: [null, []],
      invites: [null, []],
      weight: [null, []],
      public: [1, [Validators.min(1), Validators.max(3)]],
      publicUsers: [null, []],
    });
  }

  submit() {
    this.submitting = true;
    setTimeout(() => {
      this.submitting = false;
      this.msg.success(`提交成功`);
      this.cdr.detectChanges();
    }, 1000);
  }
}
