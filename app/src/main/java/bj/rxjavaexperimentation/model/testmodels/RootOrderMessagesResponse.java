
package bj.rxjavaexperimentation.model.testmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import bj.rxjavaexperimentation.model.common.Pagination;

public class RootOrderMessagesResponse
{

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;

    public Pagination getPagination()
    {
        return pagination;
    }

    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }

}
