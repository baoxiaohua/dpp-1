import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataProcessor } from 'app/shared/model/core/data-processor.model';
import { DataProcessorService } from './data-processor.service';
import { DataProcessorComponent } from './data-processor.component';
import { DataProcessorDetailComponent } from './data-processor-detail.component';
import { DataProcessorUpdateComponent } from './data-processor-update.component';
import { DataProcessorDeletePopupComponent } from './data-processor-delete-dialog.component';
import { IDataProcessor } from 'app/shared/model/core/data-processor.model';

@Injectable({ providedIn: 'root' })
export class DataProcessorResolve implements Resolve<IDataProcessor> {
    constructor(private service: DataProcessorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DataProcessor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DataProcessor>) => response.ok),
                map((dataProcessor: HttpResponse<DataProcessor>) => dataProcessor.body)
            );
        }
        return of(new DataProcessor());
    }
}

export const dataProcessorRoute: Routes = [
    {
        path: 'data-processor',
        component: DataProcessorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DataProcessors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-processor/:id/view',
        component: DataProcessorDetailComponent,
        resolve: {
            dataProcessor: DataProcessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataProcessors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-processor/new',
        component: DataProcessorUpdateComponent,
        resolve: {
            dataProcessor: DataProcessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataProcessors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-processor/:id/edit',
        component: DataProcessorUpdateComponent,
        resolve: {
            dataProcessor: DataProcessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataProcessors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataProcessorPopupRoute: Routes = [
    {
        path: 'data-processor/:id/delete',
        component: DataProcessorDeletePopupComponent,
        resolve: {
            dataProcessor: DataProcessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataProcessors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
