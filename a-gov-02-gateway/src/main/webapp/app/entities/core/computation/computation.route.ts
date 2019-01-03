import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Computation } from 'app/shared/model/core/computation.model';
import { ComputationService } from './computation.service';
import { ComputationComponent } from './computation.component';
import { ComputationDetailComponent } from './computation-detail.component';
import { ComputationUpdateComponent } from './computation-update.component';
import { ComputationDeletePopupComponent } from './computation-delete-dialog.component';
import { IComputation } from 'app/shared/model/core/computation.model';

@Injectable({ providedIn: 'root' })
export class ComputationResolve implements Resolve<IComputation> {
    constructor(private service: ComputationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Computation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Computation>) => response.ok),
                map((computation: HttpResponse<Computation>) => computation.body)
            );
        }
        return of(new Computation());
    }
}

export const computationRoute: Routes = [
    {
        path: 'computation',
        component: ComputationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Computations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'computation/:id/view',
        component: ComputationDetailComponent,
        resolve: {
            computation: ComputationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Computations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'computation/new',
        component: ComputationUpdateComponent,
        resolve: {
            computation: ComputationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Computations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'computation/:id/edit',
        component: ComputationUpdateComponent,
        resolve: {
            computation: ComputationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Computations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const computationPopupRoute: Routes = [
    {
        path: 'computation/:id/delete',
        component: ComputationDeletePopupComponent,
        resolve: {
            computation: ComputationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Computations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
