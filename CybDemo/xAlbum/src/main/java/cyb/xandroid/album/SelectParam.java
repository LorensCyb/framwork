package cyb.xandroid.album;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cyb.xandroid.album.model.LocalMedia;

/**
 * Created by chenyangbo on 2017/7/1.
 */

public class SelectParam implements Serializable {

    public final static int MODE_MULTIPLE = 1;
    public final static int MODE_SINGLE = 2;

    public final static String BUNDLE_CAMERA_PATH = "CameraPath";
    public final static String REQUEST_OUTPUT = "outputList";
    public final static String EXTRA_SELECT_MODE = "SelectMode";
    public final static String EXTRA_SHOW_CAMERA = "ShowCamera";
    public final static String EXTRA_ENABLE_PREVIEW = "EnablePreview";
    public final static String EXTRA_ENABLE_CROP = "EnableCrop";
    public final static String EXTRA_MAX_SELECT_NUM = "MaxSelectNum";

    public int maxSelectNum = 9;
    public int selectMode = MODE_MULTIPLE;
    public boolean takePhotoEnable = true;
    public boolean previewEnable = true;
    public boolean cropEnable = false;

    private List<LocalMedia> selectedList = null;

    /**
     * @param maxSelectNum    --> 可选择图片的数量
     * @param selectMode      --> 单选 or 多选
     * @param takePhotoEnable --> 是否显示拍照选项
     * @param previewEnable   --> 是否打开预览选项
     * @param cropEnable      --> 是否打开剪切选项
     * @param selectedList    -->已经选择中的图片
     */
    public SelectParam(int maxSelectNum, int selectMode,
                       boolean takePhotoEnable, boolean previewEnable,
                       boolean cropEnable, List<LocalMedia> selectedList) {

        this.maxSelectNum = maxSelectNum;
        this.selectMode = selectMode;
        this.takePhotoEnable = takePhotoEnable;
        this.previewEnable = previewEnable;
        this.cropEnable = cropEnable;
        if (selectedList != null) {
            this.selectedList = selectedList;
        } else {
            this.selectedList = new ArrayList<>();
        }

    }

    public List<LocalMedia> getSelectedList() {
        return selectedList;
    }

    public void addSelectedPhoto(int position, LocalMedia media) {
        selectedList.add(position, media);
    }

    public void addSelectedPhoto(LocalMedia media) {
        selectedList.add(media);
    }

    public void removeSelectedPhoto(int position) {
        selectedList.remove(position);
    }

    public List<String> getSelectedPhotoUri() {
        ArrayList<String> images = new ArrayList<>();
        for (LocalMedia media : selectedList) {
            images.add(media.getPath());
        }
        return images;
    }

}
