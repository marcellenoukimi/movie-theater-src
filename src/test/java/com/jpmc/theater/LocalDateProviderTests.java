package com.jpmc.theater;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@PrepareForTest({ LocalDateProvider.class })
@RunWith(PowerMockRunner.class)
public class LocalDateProviderTests {
	@Spy
	@Mock
	private LocalDateProvider ldp;

	@SuppressWarnings("static-access")
	@InjectMocks
	private LocalDateProvider l = ldp.singleton();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LocalDateProvider.class);
		ldp=Mockito.mock(LocalDateProvider.class);
		when(LocalDateProvider.singleton()).thenReturn(ldp);

	}
	@After
	public void resetSingleton() throws Exception {
		Field instance = LocalDateProvider.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
	}	

	@Test
	public void makeSureCurrentDate() throws NullPointerException{
		Whitebox.setInternalState(LocalDateProvider.class, "instance", ldp);
		assertEquals(l.currentDate(), LocalDate.now());
		assertThat(l.currentDate(), is(LocalDate.now()));
	}
}
