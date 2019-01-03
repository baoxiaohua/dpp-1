import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ComputationGroup } from 'app/shared/model/core/computation-group.model';
import { ComputationGroupService } from './computation-group.service';
import { ComputationGroupComponent } from './computation-group.component';
import { ComputationGroupDetailComponent } from './computation-group-detail.component';
import { ComputationGroupUpdateComponent } from './computation-group-update.component';
import { ComputationGroupDeletePopupComponent } from './computation-group-delete-dialog.component';
import { IComputationGroup } from 'app/shared/model/core/computation-group.model';

@Injectable({ providedIn: 'root' })
export class ComputationGroupResolve implements Resolve<IComputationGroup> {
    constructor(private service: ComputationGroupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ComputationGroup> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ComputationGroup>) => response.ok),
                map((computationGroup: HttpResponse<ComputationGroup>) => computationGroup.body)
            );
        }
        return of(new ComputationGroup());
    }
}

export const computationGroupRoute: Routes = [
    {
        path: 'computation-group',
        component: ComputationGroupComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ComputationGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'computation-group/:id/view',
        component: ComputationGroupDetailComponent,
        resolve: {
            computationGroup: ComputationGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ComputationGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'computation-group/new',
        component: ComputationGroupUpdateComponent,
        resolve: {
            computationGroup: ComputationGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ComputationGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'computation-group/:id/edit',
        component: ComputationGroupUpdateComponent,
        resolve: {
            computationGroup: ComputationGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ComputationGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const computationGroupPopupRoute: Routes = [
    {
        path: 'computation-group/:id/delete',
        component: ComputationGroupDeletePopupComponent,
        resolve: {
            computationGroup: ComputationGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ComputationGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
