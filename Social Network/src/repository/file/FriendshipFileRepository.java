package repository.file;

import domain.Friendship;
import domain.FriendshipState;
import repository.FileRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FriendshipFileRepository extends FileRepository<Friendship> {
    public FriendshipFileRepository(String file) {
        super(file);
    }

    @Override
    protected Friendship extractEntity(List<String> toExtract) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return new Friendship(Integer.parseInt(toExtract.get(0)), Integer.parseInt(toExtract.get(1)),
                Integer.parseInt(toExtract.get(2)), LocalDateTime.parse(toExtract.get(3), dateTimeFormatter), FriendshipState.valueOf(toExtract.get(4)));
    }

    @Override
    protected String createEntityAsString(Friendship entity) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return entity.getID() + ";" + entity.getUser1() + ";" + entity.getUser2() + ";" + entity.getDateCreated().format(dateTimeFormatter) + ";" + entity.getFriendshipState();
    }
}
