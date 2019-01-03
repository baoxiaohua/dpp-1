import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComputationGroup } from 'app/shared/model/core/computation-group.model';

@Component({
    selector: 'jhi-computation-group-detail',
    templateUrl: './computation-group-detail.component.html'
})
export class ComputationGroupDetailComponent implements OnInit {
    computationGroup: IComputationGroup;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ computationGroup }) => {
            this.computationGroup = computationGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
