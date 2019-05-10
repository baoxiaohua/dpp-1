import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { FeedbackRoutingModule } from './feedback-routing.module';
import { FeedbackProblemFeedbackComponent } from './problem-feedback/problem-feedback.component';


const COMPONENTS = [
  FeedbackProblemFeedbackComponent];
const COMPONENTS_NOROUNT = [ ];

@NgModule({
  imports: [
    SharedModule,
    FeedbackRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class FeedbackModule { }
