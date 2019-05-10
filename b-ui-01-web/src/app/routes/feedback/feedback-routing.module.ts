import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FeedbackProblemFeedbackComponent } from './problem-feedback/problem-feedback.component';

const routes: Routes = [

  { path: 'problem-feedback', component: FeedbackProblemFeedbackComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeedbackRoutingModule { }
