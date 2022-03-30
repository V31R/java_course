package kalchenko.external;

import kalchenko.taskDTOLayer.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ExternalTaskMapper {

    @Mappings({
            @Mapping(source = "description", target = "name"),
            @Mapping(source = "done", target = "completed")
    })
    ExternalTask toExternalTask(TaskDTO taskDTO);

    @Mappings({
            @Mapping(source = "name", target = "description"),
            @Mapping(source = "completed", target = "done")
    })
    TaskDTO toDTO(ExternalTask externalTask);

}
