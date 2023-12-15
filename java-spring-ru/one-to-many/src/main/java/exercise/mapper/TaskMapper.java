package exercise.mapper;

import exercise.dto.*;
import exercise.model.Task;
import exercise.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    // BEGIN
    public abstract Task map(TaskCreateDTO dto);
    @Mapping(target = "assigneeId", source = "assignee.id")
    public abstract TaskDTO map(Task model);
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);
    // END

}
