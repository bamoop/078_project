package com_t.macvision.mv_078.model.CloudModel;


import java.io.Serializable;

/**
 * Created by bzmoop on 2016/9/22 0022.
 */

public class CloudBaseModel<T> implements Serializable {
        public String errcode;
        public String errmsg;
        public T data;

        public boolean success() {
            return errcode.equals("0");
        }

        @Override
        public String toString() {
            return "BaseModel{" +
                    "errno='" + errcode + '\'' +
                    ", msg='" + errmsg + '\'' +
                    ",data=" + data + '}';
        }
}
