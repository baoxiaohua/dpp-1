import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IComputation } from 'app/shared/model/core/computation.model';
import { ComputationService } from './computation.service';

@Component({
    selector: 'jhi-computation-update',
    templateUrl: './computation-update.component.html'
})
export class ComputationUpdateComponent implements OnInit {
    computation: IComputation;
    isSaving: boolean;
    createTs: string;
    updateTs: string;

    constructor(private computationService: ComputationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ computation }) => {
            this.computation = computation;
            this.createTs = this.computation.createTs != null ? this.computation.createTs.format(DATE_TIME_FORMAT) : null;
            this.updateTs = this.computation.updateTs != null ? this.computation.updateTs.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.computation.createTs = this.createTs != null ? moment(this.createTs, DATE_TIME_FORMAT) : null;
        this.computation.updateTs = this.updateTs != null ? moment(this.updateTs, DATE_TIME_FORMAT) : null;
        if (this.computation.id !== undefined) {
            this.subscribeToSaveResponse(this.computationService.update(this.computation));
        } else {
            this.subscribeToSaveResponse(this.computationService.create(this.computation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComputation>>) {
        result.subscribe((res: HttpResponse<IComputation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
