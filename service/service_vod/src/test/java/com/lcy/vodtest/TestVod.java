package com.lcy.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {

    public static void main(String[] args) throws ClientException {
        String accessKeyId="LTAI5tFNJm1Bw2TJ8upcAK8B";
        String accessKeySecret="GXKInxTLCXm9RK1gq8AkgYzdTFOat7";
        String title="adad_aliyun";
        String fileName="C:/Plugins/6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        request.setPartSize(2 * 1024 * 1024L);
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
    public static void getPlayAuth() throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tFNJm1Bw2TJ8upcAK8B","GXKInxTLCXm9RK1gq8AkgYzdTFOat7");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("47001b31969c499581f42ca4b3354d33");
        response= client.getAcsResponse(request);
        System.out.println("playAuth"+response.getPlayAuth());
    }

    public static void getPlayUrl() throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tFNJm1Bw2TJ8upcAK8B","GXKInxTLCXm9RK1gq8AkgYzdTFOat7");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        request.setVideoId("47001b31969c499581f42ca4b3354d33");
        response= client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList=response.getPlayInfoList();
        for(GetPlayInfoResponse.PlayInfo playInfo:playInfoList){
            System.out.println("PlayInfo.playInfoURL="+playInfo.getPlayURL()+"\n");
        }
        System.out.println("VideoBase.Title="+response.getVideoBase().getTitle()+"\n");
    }

}

