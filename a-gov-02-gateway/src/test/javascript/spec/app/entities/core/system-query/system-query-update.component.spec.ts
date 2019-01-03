/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SystemQueryUpdateComponent } from 'app/entities/core/system-query/system-query-update.component';
import { SystemQueryService } from 'app/entities/core/system-query/system-query.service';
import { SystemQuery } from 'app/shared/model/core/system-query.model';

describe('Component Tests', () => {
    describe('SystemQuery Management Update Component', () => {
        let comp: SystemQueryUpdateComponent;
        let fixture: ComponentFixture<SystemQueryUpdateComponent>;
        let service: SystemQueryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SystemQueryUpdateComponent]
            })
                .overrideTemplate(SystemQueryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SystemQueryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemQueryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SystemQuery(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.systemQuery = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SystemQuery();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.systemQuery = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
