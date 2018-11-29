package com.lawschool.service;

import com.lawschool.beans.Collection;
import com.lawschool.beans.User;

public interface CollectionService {

    int delCollection(Collection collection, User user);

    int addCollection(Collection collection,User user);


}
