import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComputation } from 'app/shared/model/core/computation.model';

@Component({
    selector: 'jhi-computation-detail',
    templateUrl: './computation-detail.component.html'
})
export class ComputationDetailComponent implements OnInit {
    computation: IComputation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ computation }) => {
            this.computation = computation;
        });
    }

    previousState() {
        window.history.back();
    }
}
