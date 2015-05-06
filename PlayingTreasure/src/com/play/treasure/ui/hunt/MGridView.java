package com.play.treasure.ui.hunt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/** 
* @ClassName: MGridView
* @Description: 显示子类gridview
* @author wangchao29
* @date 2014年12月7日 下午4:05:51
* 
*/ 
public class MGridView extends GridView
{
    public MGridView(Context context)
    {
        super(context);
    }

    public MGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MGridView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
