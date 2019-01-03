import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemQuery } from 'app/shared/model/core/system-query.model';

@Component({
    selector: 'jhi-system-query-detail',
    templateUrl: './system-query-detail.component.html'
})
export class SystemQueryDetailComponent implements OnInit {
    systemQuery: ISystemQuery;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ systemQuery }) => {
            this.systemQuery = systemQuery;
        });
    }

    previousState() {
        window.history.back();
    }
}
