/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ComputationGroupUpdateComponent } from 'app/entities/core/computation-group/computation-group-update.component';
import { ComputationGroupService } from 'app/entities/core/computation-group/computation-group.service';
import { ComputationGroup } from 'app/shared/model/core/computation-group.model';

describe('Component Tests', () => {
    describe('ComputationGroup Management Update Component', () => {
        let comp: ComputationGroupUpdateComponent;
        let fixture: ComponentFixture<ComputationGroupUpdateComponent>;
        let service: ComputationGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ComputationGroupUpdateComponent]
            })
                .overrideTemplate(ComputationGroupUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ComputationGroupUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComputationGroupService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ComputationGroup(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.computationGroup = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ComputationGroup();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.computationGroup = entity;
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
