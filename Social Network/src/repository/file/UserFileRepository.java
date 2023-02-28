package repository.file;

import domain.User;
import domain.validators.Validator;
import repository.FileRepository;

import java.util.List;

public class UserFileRepository extends FileRepository<User> {
    public UserFileRepository(String file) {
        super(file);
    }

    @Override
    protected User extractEntity(List<String> toExtract) {
        return new User(Integer.parseInt(toExtract.get(0)), toExtract.get(1), toExtract.get(2), toExtract.get(3), toExtract.get(4));
    }

    @Override
    protected String createEntityAsString(User user) {
        return user.getID() + ";" + user.getFirstName() + ";" + user.getLastName() + ";" + user.getEmail() + ";" + user.getPassword();
    }
}
