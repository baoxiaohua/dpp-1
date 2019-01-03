import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IComputationGroup } from 'app/shared/model/core/computation-group.model';
import { ComputationGroupService } from './computation-group.service';

@Component({
    selector: 'jhi-computation-group-update',
    templateUrl: './computation-group-update.component.html'
})
export class ComputationGroupUpdateComponent implements OnInit {
    computationGroup: IComputationGroup;
    isSaving: boolean;
    createTs: string;
    updateTs: string;

    constructor(private computationGroupService: ComputationGroupService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ computationGroup }) => {
            this.computationGroup = computationGroup;
            this.createTs = this.computationGroup.createTs != null ? this.computationGroup.createTs.format(DATE_TIME_FORMAT) : null;
            this.updateTs = this.computationGroup.updateTs != null ? this.computationGroup.updateTs.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.computationGroup.createTs = this.createTs != null ? moment(this.createTs, DATE_TIME_FORMAT) : null;
        this.computationGroup.updateTs = this.updateTs != null ? moment(this.updateTs, DATE_TIME_FORMAT) : null;
        if (this.computationGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.computationGroupService.update(this.computationGroup));
        } else {
            this.subscribeToSaveResponse(this.computationGroupService.create(this.computationGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComputationGroup>>) {
        result.subscribe((res: HttpResponse<IComputationGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
