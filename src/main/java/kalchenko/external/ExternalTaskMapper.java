package kalchenko.external;

import kalchenko.taskDTOLayer.TaskDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ExternalTaskMapper {

    ExternalTask toExternalTask(TaskDTO taskDTO);
    TaskDTO toDTO(ExternalTask externalTask);

}
