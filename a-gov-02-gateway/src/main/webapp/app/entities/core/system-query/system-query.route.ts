import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SystemQuery } from 'app/shared/model/core/system-query.model';
import { SystemQueryService } from './system-query.service';
import { SystemQueryComponent } from './system-query.component';
import { SystemQueryDetailComponent } from './system-query-detail.component';
import { SystemQueryUpdateComponent } from './system-query-update.component';
import { SystemQueryDeletePopupComponent } from './system-query-delete-dialog.component';
import { ISystemQuery } from 'app/shared/model/core/system-query.model';

@Injectable({ providedIn: 'root' })
export class SystemQueryResolve implements Resolve<ISystemQuery> {
    constructor(private service: SystemQueryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<SystemQuery> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SystemQuery>) => response.ok),
                map((systemQuery: HttpResponse<SystemQuery>) => systemQuery.body)
            );
        }
        return of(new SystemQuery());
    }
}

export const systemQueryRoute: Routes = [
    {
        path: 'system-query',
        component: SystemQueryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SystemQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'system-query/:id/view',
        component: SystemQueryDetailComponent,
        resolve: {
            systemQuery: SystemQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'system-query/new',
        component: SystemQueryUpdateComponent,
        resolve: {
            systemQuery: SystemQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'system-query/:id/edit',
        component: SystemQueryUpdateComponent,
        resolve: {
            systemQuery: SystemQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemQueries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const systemQueryPopupRoute: Routes = [
    {
        path: 'system-query/:id/delete',
        component: SystemQueryDeletePopupComponent,
        resolve: {
            systemQuery: SystemQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SystemQueries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
