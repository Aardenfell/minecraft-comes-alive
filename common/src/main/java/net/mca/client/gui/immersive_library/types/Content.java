package net.mca.client.gui.immersive_library.types;

import java.util.Set;

public record Content(int contentid, int userid, String username, int likes, Set<String> tags, String title, int version, String meta, String data) implements Tagged {
    @Override
    public String toString() {
        return "Content{" +
                "contentid=" + contentid +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", likes=" + likes +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", version=" + version +
                ", meta='" + meta + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
