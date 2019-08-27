package com.example.coustombanner.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/18.
 */

public class GlideUtils {
    private static final String TAG = GlideUtils.class.getSimpleName();

    /*只播放一次GIF*/
    public static void loadOneTimeGif(Context context, Object model, final ImageView imageView, final GifListener gifListener) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        Glide.with(context).asGif().load(model).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                try {
                    Field gifStateField = GifDrawable.class.getDeclaredField("state");
                    gifStateField.setAccessible(true);
                    Class gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable$GifState");
                    Field gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader");
                    gifFrameLoaderField.setAccessible(true);
                    Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader");
                    Field gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder");
                    gifDecoderField.setAccessible(true);
                    Class gifDecoderClass = Class.forName("com.bumptech.glide.gifdecoder.GifDecoder");
                    Object gifDecoder = gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(resource)));
                    Method getDelayMethod = gifDecoderClass.getDeclaredMethod("getDelay", int.class);
                    getDelayMethod.setAccessible(true);
                    //设置只播放一次
                    resource.setLoopCount(1);
                    //获得总帧数
                    int count = resource.getFrameCount();
                    int delay = 0;
                    for (int i = 0; i < count; i++) {
                        //计算每一帧所需要的时间进行累加
                        delay += (int) getDelayMethod.invoke(gifDecoder, i);
                    }
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (gifListener != null) {
                                gifListener.gifPlayComplete();
                            }
                        }
                    }, delay);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }).into(imageView);
    }

    /**
     * Gif播放完毕回调
     */
    public interface GifListener {
        void gifPlayComplete();
    }

    public static void loadUrlImgAndGIF(Context context, Object model, RequestOptions requestOptions, final ImageView imageView) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        if (model.toString().toLowerCase().endsWith("gif")) {
            loadGIF(context, model, requestOptions, imageView);
        } else {
            loadImage(context, model, requestOptions, imageView);
        }
    }

    public static void loadUrlImgAndGIF(Context context, Object model, final ImageView imageView) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        loadUrlImgAndGIF(context, model, null, imageView);
    }

    public static void loadGIF(Context context, Object model, RequestOptions requestOptions, final ImageView imageView) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        /*当view为空，glide会报错。所以增加判定。*/
        if (imageView == null) {
            return;
        }
        if (context != null) {
            if (requestOptions == null) {
                Glide.with(context).asGif().load(model).into(imageView);
            } else {
                Glide.with(context).asGif().load(model).apply(requestOptions).into(imageView);
            }

        }
    }

    public static void loadGIF(Context context, Object model, final ImageView imageView) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        loadGIF(context, model, null, imageView);
    }

    public static void loadGIFCircleCrop(Context context, Object model, final ImageView imageView) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        /*当view为空，glide会报错。所以增加判定。*/
        if (imageView == null) {
            return;
        }
        if (context != null) {
            Glide.with(context).asGif().load(model).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
        }

    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     * 友盟统计报错信息如下
     * java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
     */
    public static void loadImage(Context context, Object model, final ImageView imageView) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        /*当view为空，glide会报错。所以增加判定。*/
        if (imageView == null) {
            return;
        }
        if (context != null) {
            Glide.with(context).load(model).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     * 友盟统计报错信息如下
     * java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
     */
    public static void loadImage(Context context, Object model, RequestOptions requestOptions, final ImageView imageView) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }

        /*当view为空，glide会报错。所以增加判定。*/
        if (imageView == null) {
            return;
        }
        if (context != null) {
            Glide.with(context).asDrawable().load(model).apply(requestOptions).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     * 友盟统计报错信息如下
     * java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
     */
    public static void loadImageCircleCrop(Context context, Object model, final ImageView imageView) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
        }
        /*当view为空，glide会报错。所以增加判定。*/
        if (imageView == null) {
            return;
        }
        if (context != null) {
            Glide.with(context).asDrawable().load(model).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }


    /**
     * 清除内存缓存.
     */
    public static void clearMemoryCache(Context context) {
        // This method must be called on the main thread.
        System.gc();
        // Glide.get(context).clearMemory();
        Glide.get(context).clearMemory();
    }


    /**
     * 清除磁盘缓存.必须在子线程里做
     */
    public static void clearDiskCache(final Context context) {
        System.gc();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // This method must be called on a background thread.
//                Glide.get(context).clearMemory();
                Glide.get(context).clearDiskCache();
                return null;
            }
        };
    }


    @GlideModule
    public final class MyGlideModule extends AppGlideModule {
        /**
         * MemorySizeCalculator类通过考虑设备给定的可用内存和屏幕大小想出合理的默认大小. * 通过LruResourceCache进行缓存。 * @param context * @param builder
         */
        @Override
        public void applyOptions(Context context, GlideBuilder builder) {
        /*MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context) .setMemoryCacheScreens(2) .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));*/
            int diskCacheSizeBytes = 1024 * 1024 * 500; // 100 MB
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));


        }

        @Override
        public boolean isManifestParsingEnabled() {
            return false;
        }


    }


}
