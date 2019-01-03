import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISystemQuery } from 'app/shared/model/core/system-query.model';
import { SystemQueryService } from './system-query.service';

@Component({
    selector: 'jhi-system-query-update',
    templateUrl: './system-query-update.component.html'
})
export class SystemQueryUpdateComponent implements OnInit {
    systemQuery: ISystemQuery;
    isSaving: boolean;
    createTs: string;
    updateTs: string;

    constructor(private systemQueryService: SystemQueryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ systemQuery }) => {
            this.systemQuery = systemQuery;
            this.createTs = this.systemQuery.createTs != null ? this.systemQuery.createTs.format(DATE_TIME_FORMAT) : null;
            this.updateTs = this.systemQuery.updateTs != null ? this.systemQuery.updateTs.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.systemQuery.createTs = this.createTs != null ? moment(this.createTs, DATE_TIME_FORMAT) : null;
        this.systemQuery.updateTs = this.updateTs != null ? moment(this.updateTs, DATE_TIME_FORMAT) : null;
        if (this.systemQuery.id !== undefined) {
            this.subscribeToSaveResponse(this.systemQueryService.update(this.systemQuery));
        } else {
            this.subscribeToSaveResponse(this.systemQueryService.create(this.systemQuery));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISystemQuery>>) {
        result.subscribe((res: HttpResponse<ISystemQuery>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
