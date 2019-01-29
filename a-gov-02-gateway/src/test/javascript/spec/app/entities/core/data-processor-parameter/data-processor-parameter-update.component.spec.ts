/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { DataProcessorParameterUpdateComponent } from 'app/entities/core/data-processor-parameter/data-processor-parameter-update.component';
import { DataProcessorParameterService } from 'app/entities/core/data-processor-parameter/data-processor-parameter.service';
import { DataProcessorParameter } from 'app/shared/model/core/data-processor-parameter.model';

describe('Component Tests', () => {
    describe('DataProcessorParameter Management Update Component', () => {
        let comp: DataProcessorParameterUpdateComponent;
        let fixture: ComponentFixture<DataProcessorParameterUpdateComponent>;
        let service: DataProcessorParameterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataProcessorParameterUpdateComponent]
            })
                .overrideTemplate(DataProcessorParameterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataProcessorParameterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataProcessorParameterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataProcessorParameter(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataProcessorParameter = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataProcessorParameter();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataProcessorParameter = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
