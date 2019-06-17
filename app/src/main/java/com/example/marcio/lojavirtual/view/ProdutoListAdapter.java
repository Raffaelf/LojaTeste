package com.example.marcio.lojavirtual.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marcio.lojavirtual.R;
import com.example.marcio.lojavirtual.Api_manipulation.Url_config;
import com.example.marcio.lojavirtual.model.Produto;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

public class ProdutoListAdapter extends ArrayAdapter<Produto> {

    private static final String TAG = "ProductListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView nome;
        TextView preco;
        TextView qtd;
        ImageView image;
    }


    public ProdutoListAdapter(Context context, int resource, List<Produto> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the persons information
        String nome = getItem(position).getNome();
        double preco = getItem(position).getPreco();
        double qtd = getItem(position).getQnt();
        String imgUrl = Url_config.API_URL + "img/" + getItem(position).getImagens().get(0).getId().toString();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.nome = (TextView) convertView.findViewById(R.id.tv_nome);
            holder.preco = (TextView) convertView.findViewById(R.id.tv_preco);
            holder.qtd = (TextView) convertView.findViewById(R.id.tv_qtd);
            holder.image = (ImageView) convertView.findViewById(R.id.img_v);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.nome.setText("Nome: "+ nome);
        holder.preco.setText("Preço: "+ String.valueOf(preco));
        holder.qtd.setText("Quantidade: " + String.valueOf(qtd));

        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();
        ;
        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed", null, mContext.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(imgUrl, holder.image, options);

        return convertView;
    }

    private void setupImageLoader() {
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
